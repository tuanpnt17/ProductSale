using AutoMapper;
using ProductSale.Business.Enums;
using ProductSale.Business.Models;
using ProductSale.Business.Token;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Business.User
{
    public class UserService(
        IUserRepository userRepository,
        IMapper mapper,
        ITokenService tokenService
    ) : IUserService
    {
        public async Task<(string, UserResponseDto)> LoginAsync(LoginDto loginDto)
        {
            var user = await userRepository.GetUserByUsernameAsync(loginDto.Username);
            if (user == null || !BCrypt.Net.BCrypt.Verify(loginDto.Password, user.PasswordHash))
            {
                throw new Exception("Invalid username or password");
            }
            return new(
                tokenService.GenerateToken(user),
                mapper.Map<Repository.Entities.User, UserResponseDto>(user)
            );
        }

        public async Task<(string, UserResponseDto)> RegisterAsync(RegistrationDto registrationDto)
        {
            var existingUserByUsername = await userRepository.GetUserByUsernameAsync(
                registrationDto.Username
            );
            if (existingUserByUsername != null)
            {
                throw new Exception("Username already exists");
            }

            var existingUserByEmail = await userRepository.GetUserByEmailAsync(
                registrationDto.Email
            );
            if (existingUserByEmail != null)
            {
                throw new Exception("Email already exists");
            }

            var user = mapper.Map<RegistrationDto, Repository.Entities.User>(registrationDto);
            user.Role = Role.Customer.ToString();
            user.PasswordHash = BCrypt.Net.BCrypt.HashPassword(registrationDto.Password);
            user = await userRepository.RegisterUserAsync(user);
            return new(
                tokenService.GenerateToken(user),
                mapper.Map<Repository.Entities.User, UserResponseDto>(user)
            );
        }
    }
}
