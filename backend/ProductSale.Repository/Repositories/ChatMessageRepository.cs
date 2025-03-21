using ProductSale.Repository.Data;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Repository.Repositories;

public class ChatMessageRepository : GenericRepository<ChatMessage>, IChatMessageRepository
{
	public ChatMessageRepository(ApplicationDbContext context) : base(context)
	{
	}
}