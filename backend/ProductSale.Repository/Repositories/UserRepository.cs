using ProductSale.Repository.Data;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Repository.Repositories;

public class UserRepository : GenericRepository<User>, IUserRepository
{
	public UserRepository(ApplicationDbContext context) : base(context)
	{
	}
}