using Microsoft.AspNetCore.Mvc;

namespace Whatsapp.VonageAPI.Controllers
{
    [ApiController]
    [Route("api/v1/[controller]/[action]")]
    public class HealthController : ControllerBase
    {
        public HealthController() { }

        [HttpGet]
        public ActionResult IsAlive()
        {
            return Ok();
        }
    }
}
