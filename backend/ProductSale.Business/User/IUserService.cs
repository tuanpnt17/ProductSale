using ProductSale.Business.Models;

namespace ProductSale.Business.User
{
    public interface IUserService
    {
        Task<(string, UserResponseDto)> LoginAsync(LoginDto loginDto);

        Task<(string, UserResponseDto)> RegisterAsync(RegistrationDto registrationDto);
    }
}
