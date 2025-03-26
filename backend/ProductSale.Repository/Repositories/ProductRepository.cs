using ProductSale.Repository.Data;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Interfaces;

namespace ProductSale.Repository.Repositories;

public class ProductRepository(ApplicationDbContext context)
    : GenericRepository<Product>(context),
        IProductRepository { }
