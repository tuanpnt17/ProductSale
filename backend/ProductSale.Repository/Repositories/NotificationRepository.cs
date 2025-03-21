using ProductSale.Repository.Data;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Repository.Repositories;

public class NotificationRepository : GenericRepository<Notification>, INotificationRepository
{
	public NotificationRepository(ApplicationDbContext context) : base(context)
	{
	}
}