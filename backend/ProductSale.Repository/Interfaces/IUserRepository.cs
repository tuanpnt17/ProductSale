using ProductSale.Repository.Entities;

namespace ProductSale.Repository.Interfaces;

public interface IUserRepository : IGenericRepository<User>
{
    Task<User?> GetUserByUsernameAsync(string username);
    Task<User?> GetUserByEmailAsync(string email);
    Task<User> RegisterUserAsync(User user);
}
