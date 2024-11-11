using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Interface.Application
{
    public interface IHttpFactoryRequests
    {
        Uri BuildUri(string baseUrl, Dictionary<string, string> parameters);
        Uri BuildUri(string baseUrl, string endpoint);
        Uri BuildUri(string baseUrl, string endpoint, Dictionary<string, string> parameters);
        Task<HttpResponseMessage> DefaultGetRequest(Uri url);
        Task<HttpResponseMessage> DefaultGetRequest(Uri url, Dictionary<string, string> headers);
        Task<HttpResponseMessage> DefaultPostRequest<T>(Uri url, T body);
        Task<HttpResponseMessage> DefaultPostRequest<T>(Uri url, T body, Dictionary<string, string> headers);
        Task<HttpResponseMessage> PostFile(Uri url, Stream content, string filename);
        Task<HttpResponseMessage> PostFile(Uri url, Stream content, string filename, Dictionary<string, string> headers);
        T ProcessResponse<T>(HttpResponseMessage response);
    }
}
