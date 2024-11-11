using Domain.Interface.Application;
using System.Net.Http.Headers;
using System.Text.Json;
using System.Web;

namespace Application.HttpRequest
{
    public class HttpFactoryRequests : IHttpFactoryRequests
    {
        private readonly IHttpClientFactory httpClientFactory;

        public HttpFactoryRequests(IHttpClientFactory httpClientFactory)
        {
            this.httpClientFactory = httpClientFactory;
        }

        public Uri BuildUri(string baseUrl, string endpoint) => BuildUri(baseUrl, endpoint, null);
        public Uri BuildUri(string baseUrl, Dictionary<string, string> parameters) => BuildUri(baseUrl, null, parameters);
        public Uri BuildUri(string baseUrl, string endpoint, Dictionary<string, string> parameters)
        {
            var builder = new UriBuilder(baseUrl);

            if (!string.IsNullOrEmpty(endpoint))
                builder.Path = endpoint;

            if (parameters != null && parameters.Count > 0)
            {
                var queryBuilder = new List<string>();

                foreach (var parameter in parameters)
                {
                    string encodedKey = HttpUtility.UrlEncode(parameter.Key);
                    string encodedValue = HttpUtility.UrlEncode(parameter.Value);
                    string queryParameter = $"{encodedKey}={encodedValue}";

                    queryBuilder.Add(queryParameter);
                }

                builder.Query = string.Join("&", queryBuilder);
            }

            return builder.Uri;
        }

        public T ProcessResponse<T>(HttpResponseMessage response)
        {
            Stream streamData = response.Content.ReadAsStreamAsync().Result;
            StreamReader reader = new StreamReader(streamData);
            string result = reader.ReadToEnd();

            try
            {
                return (T)Convert.ChangeType(result, typeof(T));
            }
            catch
            {
                return JsonSerializer.Deserialize<T>(result);
            }
        }

        public async Task<HttpResponseMessage> DefaultGetRequest(Uri url) => await DefaultGetRequest(url, null);
        public async Task<HttpResponseMessage> DefaultGetRequest(Uri url, Dictionary<string, string> headers)
        {
            try
            {
                HttpClient request = httpClientFactory.CreateClient();

                if (headers != null)
                {
                    foreach (KeyValuePair<string, string> header in headers)
                    {
                        request.DefaultRequestHeaders.Add(header.Key, header.Value);
                    }
                }

                HttpResponseMessage response = await request.GetAsync(url);

                return response;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public async Task<HttpResponseMessage> DefaultPostRequest<T>(Uri url, T body) => await DefaultPostRequest(url, body, null);
        public async Task<HttpResponseMessage> DefaultPostRequest<T>(Uri url, T body, Dictionary<string, string> headers)
        {
            try
            {
                HttpClient request = httpClientFactory.CreateClient();
                StringContent content = new StringContent(JsonSerializer.Serialize(body));
                content.Headers.ContentType = MediaTypeHeaderValue.Parse("application/json");

                if (headers != null)
                {
                    foreach (KeyValuePair<string, string> header in headers)
                    {
                        request.DefaultRequestHeaders.Add(header.Key, header.Value);
                    }
                }

                HttpResponseMessage response = await request.PostAsync(url, content);

                return response;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public async Task<HttpResponseMessage> PostFile(Uri url, Stream content, string filename) => await PostFile(url, content, filename, null);
        public async Task<HttpResponseMessage> PostFile(Uri url, Stream content, string filename, Dictionary<string, string> headers)
        {
            try
            {
                HttpClient request = httpClientFactory.CreateClient();
                MultipartFormDataContent multipartFormContent = new MultipartFormDataContent();
                StreamContent fileStreamContent = new StreamContent(content);
                fileStreamContent.Headers.ContentType = new MediaTypeHeaderValue("multipart/form-data");
                multipartFormContent.Add(fileStreamContent, name: "files", fileName: filename);

                if (headers != null)
                {
                    foreach (KeyValuePair<string, string> header in headers)
                    {
                        request.DefaultRequestHeaders.Add(header.Key, header.Value);
                    }
                }

                HttpResponseMessage response = await request.PostAsync(url, multipartFormContent);

                return response;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
    }
}
