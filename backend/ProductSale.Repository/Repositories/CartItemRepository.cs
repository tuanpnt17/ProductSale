using ProductSale.Repository.Data;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Repository.Repositories;

public class CartItemRepository : GenericRepository<CartItem>, ICartItemRepository
{ 
	public CartItemRepository(ApplicationDbContext context) : base(context)
	{
	}
}