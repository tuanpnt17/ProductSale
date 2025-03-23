using AutoMapper;
using ProductSale.Business.Models;
using ProductSale.Repository.Entities;
using ProductSale.Repository.Helpers;

namespace ProductSale.API.Helpers
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            #region // ======= Category =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();
            #endregion

            #region // ======= Cart =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();
            #endregion

            #region // ======= Chat Message =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();

            #endregion

            #region // ======= Notification =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();

            #endregion

            #region // ======= Order =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();

            #endregion

            #region // ======= Payment =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();

            #endregion

            #region // ======= Product =======

            CreateMap<ProductQueryDto, ProductQueryParams>();
            CreateMap<Product, ProductSummaryDto>();

            #endregion

            #region // ======= Store Location =======
            //CreateMap<ProductSale.Repository.Entities.Category, ProductSale.Business.Category.CategoryDto>().ReverseMap();

            #endregion

            #region // ======= User =======

            CreateMap<RegistrationDto, User>();
            CreateMap<User, UserResponseDto>();

            #endregion
        }
    }
}
