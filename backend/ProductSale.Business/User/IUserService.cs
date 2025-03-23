using ProductSale.Business.Models;

namespace ProductSale.Business.User
{
    public interface IUserService
    {
        Task<string> LoginAsync(LoginDto loginDto);

        Task<string> RegisterAsync(RegistrationDto registrationDto);
    }
}
