namespace ProductSale.Business.StoreLocation
{
    public interface IStoreLocationService
    {
        public Task<Repository.Entities.StoreLocation?> GetStoreLocations();
    }
}
