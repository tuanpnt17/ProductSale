using ProductSale.Repository.Data;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Repository.Repositories;

public class OrderRepository : GenericRepository<Order>, IOrderRepository
{
	public OrderRepository(ApplicationDbContext context) : base(context)
	{
	}
}