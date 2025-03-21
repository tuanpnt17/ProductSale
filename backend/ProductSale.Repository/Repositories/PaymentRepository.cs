using ProductSale.Repository.Data;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Repository.Repositories;

public class PaymentRepository : GenericRepository<Payment>, IPaymentRepository
{
	public PaymentRepository(ApplicationDbContext context) : base(context)
	{
	}
}