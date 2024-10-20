using Domain.DTO.Message;
using Domain.Enum;
using Domain.Interface.Application;
using Microsoft.Extensions.Configuration;
using System.Text.RegularExpressions;
using System.Web;

namespace Whatsapp.VonageAPI.Services.Utils
{
    public class BuildMessages : IBuildMessages
    {
        private readonly IConfiguration _configuration;

        private static readonly Dictionary<string, string> htmlTags = new(StringComparer.OrdinalIgnoreCase)
        {
            {"<strong>", "*"},
            {"</strong>", "*"},

            {"<em>", "_"},
            {"</em>", "_"},

            {"<s>", "~"},
            {"</s>", "~"},

            {"<br>", "\n"},
            {"</br>", "\n"},

            {"<u>", ""},
            {"</u>", ""}
        };

        private static string tagCommand = "M4rsup14LM0n0tr3m4do";
        private static readonly Regex removeHtml = new(@"(\[([^\]]+)\])", RegexOptions.Compiled);

        public BuildMessages(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        public List<ConstructMessage> BuildWhatsMessages(string text)
        {
            try
            {
                string finalText = FixWhatsText(text);
                finalText = removeHtml.Replace(finalText, $"{tagCommand}$1{tagCommand}");

                List<ConstructMessage> messages = new List<ConstructMessage>();
                List<string> listMessages = finalText.Split(tagCommand).Where(x => !string.IsNullOrEmpty(x)).ToList();

                foreach (string messageText in listMessages)
                {
                    ConstructMessage message = new()
                    {
                        text = messageText,
                        messageType = DefineMessageType(messageText)
                    };

                    messages.Add(message);
                }

                List<ConstructMessage> final = SynthesizeList(messages);

                return VerifyLenght(final);
            }
            catch (Exception)
            {
                throw;
            }
        }

        private RequestMessageType DefineMessageType(string message)
        {
            if(message.StartsWith("<img"))
                return RequestMessageType.Image;

            if(message.StartsWith("<file"))
                return RequestMessageType.File;

            if(message.Contains("<newdialog/>"))
                return RequestMessageType.NewDialog;

            return RequestMessageType.Text;
        }

        private string FixWhatsText(string text)
        {
            try
            {
                string textDecode = HttpUtility.HtmlDecode(text); //remove as Html entities
                string textFixHtmlTags = htmlTags.Aggregate(textDecode, (current, value) => current.Replace(value.Key, value.Value)); //substitui as tags que são aplicaveis ao whatsapp (negrito, italicio, etc)
                return Regex.Replace(textFixHtmlTags, "(<([^>]+)>)", ""); //remove as tags html adicionais
            }
            catch (Exception)
            {
                throw;
            }
        }

        private bool IsValidUrl(string url)
        {
            return Uri.TryCreate(url, UriKind.Absolute, out Uri? uriResult) &&
                    (uriResult.Scheme == Uri.UriSchemeHttp || uriResult.Scheme == Uri.UriSchemeHttps);
        }

        private List<ConstructMessage> SynthesizeList(List<ConstructMessage> messages)
        {
            List<ConstructMessage> newList = new List<ConstructMessage>();
            string textAggregate = "";

            foreach (ConstructMessage message in messages)
            {
                if (message.messageType == RequestMessageType.NewDialog)
                {
                    if (!string.IsNullOrEmpty(textAggregate))
                    {
                        newList.Add(new ConstructMessage
                        {
                            text = textAggregate
                        });

                        textAggregate = "";
                    }
                    continue;
                }

                if (message.messageType == RequestMessageType.File || message.messageType == RequestMessageType.Image)
                {
                    if (!string.IsNullOrEmpty(textAggregate))
                    {
                        newList.Add(new ConstructMessage
                        {
                            text = textAggregate
                        });

                        textAggregate = "";
                    }

                    newList.Add(message);

                    continue;
                }

                textAggregate += message.text;
            }

            if (!string.IsNullOrEmpty(textAggregate))
            {
                newList.Add(new ConstructMessage
                {
                    text = textAggregate
                });
            }

            return newList;
        }

        private List<ConstructMessage> VerifyLenght(List<ConstructMessage> messages)
        {
            List<ConstructMessage> verifyMessages = new List<ConstructMessage>();

            foreach (ConstructMessage message in messages)
            {
                if (message.text.Length < 4000)
                {
                    verifyMessages.Add(message);
                }
                else
                {
                    verifyMessages.AddRange(BreakText(message));
                }
            }

            return verifyMessages;
        }

        private List<ConstructMessage> BreakText(ConstructMessage message)
        {
            List<string> terminators = new List<string>()
            {"(", "[", "{", ")", "]", "}", ",", ":", ";", ".", "?", "!", " "};

            List<ConstructMessage> breakMessages = new List<ConstructMessage>();
            string text = message.text;

            while (text.Length > 3900)
            {
                int index = -1;
                int lastCharacter = 0;

                foreach (string terminator in terminators)
                {
                    index = text.Substring(0, 3900).LastIndexOf(terminator);

                    if (index > lastCharacter)
                        lastCharacter = index;
                }

                lastCharacter = lastCharacter == 0 ? 3900 : lastCharacter;
                lastCharacter++;
                breakMessages.Add(new ConstructMessage { text = text.Substring(0, lastCharacter) });
                text = text.Substring(lastCharacter, text.Length - lastCharacter);
            }

            if (text.Length > 0)
                breakMessages.Add(new ConstructMessage { text = text });

            return breakMessages;
        }
    }
}
