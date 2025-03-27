using Microsoft.EntityFrameworkCore;
using ProductSale.Repository.Data;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Repository.Repositories;

public class UserRepository : GenericRepository<User>, IUserRepository
{
    private readonly ApplicationDbContext _context;

    public UserRepository(ApplicationDbContext context)
        : base(context)
    {
        _context = context;
    }

    public Task<User?> GetUserByUsernameAsync(string username)
    {
        return _context.Users.FirstOrDefaultAsync(u => u.Username == username);
    }

    public Task<User?> GetUserByEmailAsync(string email)
    {
        return _context.Users.FirstOrDefaultAsync(u => u.Email == email);
    }

    public async Task<User> RegisterUserAsync(User user)
    {
        _context.Users.Add(user);
        await _context.SaveChangesAsync();
        return user;
    }
}
