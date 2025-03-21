using ProductSale.Repository.Data;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Repository.Repositories;

public class CartRepository : GenericRepository<Cart>, ICartRepository
{
	public CartRepository(ApplicationDbContext context) : base(context)
	{
	}
}