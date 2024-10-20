using Domain.DTO.Vonage;
using Domain.Interface.Application;
using Microsoft.AspNetCore.Mvc;

namespace Whatsapp.VonageAPI.Controllers
{
    [ApiController]
    [Route("api/v1/[controller]/[action]")]
    public class VonageController : ControllerBase
    {
        private readonly IProcessVonageMessage _processVonageService;
        private readonly ILogger<VonageController> _logger;

        public VonageController(IProcessVonageMessage processVonageService, ILogger<VonageController> logger)
        {
            _processVonageService = processVonageService;
            _logger = logger;
        }

        [HttpPost]
        public async Task<ActionResult> StatusMessage([FromBody] VonageStatus status)
        {
            try
            {
                _logger.LogDebug("Recebido no Status Message: {@status}", status);

                Task.Run(() => _processVonageService.ProcessStatusMessage(status));

                return Ok();
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error on status endpoint message: {@status}", status);
                return BadRequest();
            }
        }

        [HttpPost]
        public async Task<ActionResult> Conversation([FromBody] VonageMessage message)
        {
            try
            {
                _logger.LogDebug("Recebido noConversation: {@message}", message);

                return Ok();
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error on Conversation endpoint: {@message}", message);
                return BadRequest();
            }
        }
    }
}
