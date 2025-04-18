﻿using AutoMapper;
using ProductSale.API.Models.Categories;
using ProductSale.API.Models.Products;
using ProductSale.Business.Models;
using ProductSale.Repository.Entities;

namespace ProductSale.API.Helpers
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            #region // ======= Category =======

            CreateMap<Category, CategoryVM>();

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

            CreateMap<Product, ProductSummaryVM>()
                .ForMember(
                    dest => dest.CategoryName,
                    opt =>
                        opt.MapFrom(src => src.Category == null ? "N/A" : src.Category.CategoryName)
                );

            CreateMap<Repository.Entities.Product, ProductDetailVM>()
                .ForMember(
                    dest => dest.CategoryName,
                    opt =>
                        opt.MapFrom(src => src.Category == null ? "N/A" : src.Category.CategoryName)
                )
                .ReverseMap();
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
