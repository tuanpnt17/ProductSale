﻿using System;
using System.Collections.Generic;

namespace ProductSale.Repository.Entities;

public partial class StoreLocation
{
    public int LocationId { get; set; }

    public decimal Latitude { get; set; }

    public decimal Longitude { get; set; }

    public string Address { get; set; } = null!;
}
