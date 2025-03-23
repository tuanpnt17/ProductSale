using Microsoft.AspNetCore.Mvc;
using ProductSale.Business.Models;
using ProductSale.Business.User;

namespace ProductSale.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController(IUserService userService) : ControllerBase
    {
        [HttpPost("login")]
        public async Task<IActionResult> LoginAsync([FromBody] LoginDto loginDto)
        {
            try
            {
                var (token, user) = await userService.LoginAsync(loginDto);
                return Ok(new { access_token = token, user });
            }
            catch (Exception ex)
            {
                return Unauthorized(new { message = ex.Message });
            }
        }

        [HttpPost("register")]
        public async Task<IActionResult> RegisterAsync([FromBody] RegistrationDto registrationDto)
        {
            try
            {
                var (token, user) = await userService.RegisterAsync(registrationDto);
                return Ok(new { access_token = token, user });
            }
            catch (Exception ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }
    }
}
