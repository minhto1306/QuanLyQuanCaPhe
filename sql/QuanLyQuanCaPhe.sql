USE [QuanLyQuanCaPhe]
GO
/****** Object:  Table [dbo].[KhuVuc]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhuVuc](
	[maKhuVuc] [varchar](20) NOT NULL,
	[tenKhuVuc] [nvarchar](50) NOT NULL,
	[phuThu] [decimal](18, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[maKhuVuc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Ban]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Ban](
	[maBan] [varchar](20) NOT NULL,
	[maKhuVuc] [varchar](20) NOT NULL,
	[tenBan] [nvarchar](50) NOT NULL,
	[trangThai] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[maBan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DatBan]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DatBan](
	[maDatBan] [varchar](20) NOT NULL,
	[maBan] [varchar](20) NOT NULL,
	[tenKhachHang] [nvarchar](100) NOT NULL,
	[soDienThoai] [varchar](15) NOT NULL,
	[thoiGianDat] [datetime] NOT NULL,
	[thoiGianNhanBan] [datetime] NULL,
	[trangThai] [bit] NULL,
	[maKhuVuc] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[maDatBan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[V_ChiTietDatBan]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[V_ChiTietDatBan] AS
SELECT 
    db.maDatBan, 
    b.tenBan, 
    kv.tenKhuVuc, 
    db.tenKhachHang, 
    db.soDienThoai, 
    db.thoiGianNhanBan, 
    db.trangThai
FROM DatBan db
LEFT JOIN Ban b ON db.maBan = b.maBan
LEFT JOIN KhuVuc kv ON db.maKhuVuc = kv.maKhuVuc;
GO
/****** Object:  View [dbo].[View_ChiTietDatBan]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   VIEW [dbo].[View_ChiTietDatBan] AS
SELECT 
    db.maDatBan, 
    b.tenBan, 
    kv.tenKhuVuc, 
    db.tenKhachHang, 
    db.soDienThoai, 
    db.thoiGianNhanBan, 
    db.trangThai
FROM DatBan db
LEFT JOIN Ban b ON db.maBan = b.maBan
LEFT JOIN KhuVuc kv ON db.maKhuVuc = kv.maKhuVuc;
GO
/****** Object:  Table [dbo].[CaLamViec]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CaLamViec](
	[maCa] [varchar](20) NOT NULL,
	[tenCa] [nvarchar](50) NOT NULL,
	[thoiGianBD] [time](7) NOT NULL,
	[thoiGianKT] [time](7) NOT NULL,
	[tienMatDauCa] [decimal](18, 0) NULL,
	[tongDoanhThuTrongCa] [decimal](18, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[maCa] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietDatBan]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietDatBan](
	[maDatBan] [varchar](20) NOT NULL,
	[maSanPham] [varchar](20) NOT NULL,
	[soLuong] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maDatBan] ASC,
	[maSanPham] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietHoaDon]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietHoaDon](
	[maHoaDon] [varchar](20) NOT NULL,
	[maSanPham] [varchar](20) NOT NULL,
	[soLuong] [int] NOT NULL,
	[giaBan] [decimal](18, 0) NOT NULL,
	[ghiChu] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[maHoaDon] ASC,
	[maSanPham] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DanhMuc]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DanhMuc](
	[maDanhMuc] [varchar](20) NOT NULL,
	[tenDanhMuc] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maDanhMuc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HoaDon]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDon](
	[maHoaDon] [varchar](20) NOT NULL,
	[maNhanVien] [varchar](20) NOT NULL,
	[maBan] [varchar](20) NOT NULL,
	[thoiGianLap] [datetime] NULL,
	[tongTien] [decimal](18, 0) NULL,
	[thueVAT] [decimal](18, 0) NULL,
	[giamGia] [decimal](18, 0) NULL,
	[thanhTien] [decimal](18, 0) NULL,
	[trangThai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[maHoaDon] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[maNhanVien] [varchar](20) NOT NULL,
	[hoTenNhanVien] [nvarchar](100) NOT NULL,
	[soCCCD] [varchar](20) NOT NULL,
	[soDienThoai] [varchar](15) NOT NULL,
	[tenDangNhap] [varchar](50) NULL,
	[trangThai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[maNhanVien] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhanCongCaLam]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhanCongCaLam](
	[maNhanVien] [varchar](20) NOT NULL,
	[maCa] [varchar](20) NOT NULL,
	[ngayLam] [date] NOT NULL,
	[trangThaiDiemDanh] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[maNhanVien] ASC,
	[maCa] ASC,
	[ngayLam] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SanPham]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SanPham](
	[maSanPham] [varchar](20) NOT NULL,
	[maDanhMuc] [varchar](20) NOT NULL,
	[tenSanPham] [nvarchar](100) NOT NULL,
	[giaBan] [decimal](18, 0) NOT NULL,
	[trangThai] [bit] NULL,
	[hinhAnh] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[maSanPham] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaiKhoan]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoan](
	[tenDangNhap] [varchar](50) NOT NULL,
	[matKhau] [varchar](255) NOT NULL,
	[trangThai] [bit] NULL,
	[maVaiTro] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[tenDangNhap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[VaiTro]    Script Date: 5/4/2026 10:45:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[VaiTro](
	[maVaiTro] [varchar](20) NOT NULL,
	[tenVaiTro] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maVaiTro] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV01_B1', N'KV01', N'Bàn 1', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV01_B10', N'KV01', N'Bàn 10', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV01_B11', N'KV01', N'Bàn 11', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV01_B12', N'KV01', N'Bàn 12', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV01_B2', N'KV01', N'Bàn 2', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV01_B3', N'KV01', N'Bàn 3', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV01_B4', N'KV01', N'Bàn 4', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV01_B5', N'KV01', N'Bàn 5', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV01_B6', N'KV01', N'Bàn 6', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV01_B7', N'KV01', N'Bàn 7', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV01_B8', N'KV01', N'Bàn 8', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV01_B9', N'KV01', N'Bàn 9', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV02_B1', N'KV02', N'Bàn VIP 1', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV02_B2', N'KV02', N'Bàn VIP 2', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV02_B3', N'KV02', N'Bàn VIP 3', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV02_B4', N'KV02', N'Bàn VIP 4', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV02_B5', N'KV02', N'Bàn VIP 5', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV02_B6', N'KV02', N'Bàn VIP 6', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV02_B7', N'KV02', N'Bàn VIP 7', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV02_B8', N'KV02', N'Bàn VIP 8', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV03_B1', N'KV03', N'Bàn Góc 1', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV03_B2', N'KV03', N'Bàn Góc 2', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV03_B3', N'KV03', N'Bàn Góc 3', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV03_B4', N'KV03', N'Bàn Góc 4', N'Trống')
GO
INSERT [dbo].[Ban] ([maBan], [maKhuVuc], [tenBan], [trangThai]) VALUES (N'KV03_B5', N'KV03', N'Bàn Góc 5', N'Trống')
GO
INSERT [dbo].[CaLamViec] ([maCa], [tenCa], [thoiGianBD], [thoiGianKT], [tienMatDauCa], [tongDoanhThuTrongCa]) VALUES (N'CA01', N'Ca Sáng', CAST(N'06:00:00' AS Time), CAST(N'14:00:00' AS Time), CAST(500000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)))
GO
INSERT [dbo].[CaLamViec] ([maCa], [tenCa], [thoiGianBD], [thoiGianKT], [tienMatDauCa], [tongDoanhThuTrongCa]) VALUES (N'CA02', N'Ca Chiều', CAST(N'14:00:00' AS Time), CAST(N'22:00:00' AS Time), CAST(500000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)))
GO
INSERT [dbo].[CaLamViec] ([maCa], [tenCa], [thoiGianBD], [thoiGianKT], [tienMatDauCa], [tongDoanhThuTrongCa]) VALUES (N'CA03', N'Ca Tối', CAST(N'22:00:00' AS Time), CAST(N'06:00:00' AS Time), CAST(500000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)))
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502100213', N'SP01', 3, CAST(20000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502100213', N'SP02', 2, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502100213', N'SP07', 2, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502100213', N'SP19', 1, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502100850', N'SP03', 5, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502100850', N'SP07', 3, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502100850', N'SP19', 3, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502100850', N'SP21', 2, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502100850', N'SP23', 2, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502143730', N'SP07', 7, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502143730', N'SP08', 3, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502151323', N'SP19', 7, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502153514', N'SP08', 2, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502153514', N'SP09', 2, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502153514', N'SP19', 2, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502153514', N'SP20', 2, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502153532', N'SP07', 2, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502153532', N'SP08', 3, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502153532', N'SP21', 1, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502153532', N'SP22', 2, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502175925', N'SP06', 1, CAST(20000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502175925', N'SP07', 3, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502175925', N'SP19', 3, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502175936', N'SP20', 3, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502175936', N'SP21', 2, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502175936', N'SP22', 2, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502180024', N'SP12', 3, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502180024', N'SP15', 2, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502180024', N'SP16', 1, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502180024', N'SP17', 2, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502180024', N'SP25', 4, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502180024', N'SP26', 2, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502180024', N'SP30', 1, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502180024', N'SP31', 1, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502180024', N'SP32', 2, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502180024', N'SP33', 2, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502180024', N'SP36', 2, CAST(20000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502180024', N'SP46', 3, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502214640', N'SP12', 3, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502214640', N'SP14', 2, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502214640', N'SP15', 1, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502214640', N'SP32', 3, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502214640', N'SP33', 3, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502214640', N'SP35', 1, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502214640', N'SP36', 1, CAST(20000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502214640', N'SP38', 2, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502215147', N'SP13', 3, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502215147', N'SP14', 2, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502215147', N'SP30', 1, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502215147', N'SP31', 1, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502215147', N'SP34', 1, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502215147', N'SP38', 1, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502215242', N'SP13', 3, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260502215242', N'SP14', 2, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140305', N'SP01', 1, CAST(20000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140305', N'SP03', 1, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140305', N'SP04', 1, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140305', N'SP08', 3, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140305', N'SP09', 2, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140305', N'SP19', 3, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140305', N'SP20', 4, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140305', N'SP21', 2, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140305', N'SP22', 4, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140305', N'SP26', 10, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140305', N'SP27', 4, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140305', N'SP28', 3, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140313', N'SP19', 1, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140313', N'SP20', 1, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140313', N'SP21', 1, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140313', N'SP22', 1, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140313', N'SP23', 1, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140313', N'SP25', 1, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140313', N'SP26', 1, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504140313', N'SP27', 2, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504151958', N'SP09', 3, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504151958', N'SP10', 2, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504151958', N'SP11', 1, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504151958', N'SP15', 2, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504151958', N'SP19', 2, CAST(40000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504151958', N'SP25', 2, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504151958', N'SP26', 2, CAST(45000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504151958', N'SP29', 1, CAST(20000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504151958', N'SP30', 1, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504151958', N'SP40', 2, CAST(20000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504151958', N'SP41', 1, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504151958', N'SP46', 4, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504152007', N'SP29', 1, CAST(20000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504152007', N'SP31', 1, CAST(25000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504152007', N'SP33', 1, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504152007', N'SP34', 3, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504152007', N'SP35', 1, CAST(35000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504152007', N'SP36', 1, CAST(20000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[ChiTietHoaDon] ([maHoaDon], [maSanPham], [soLuong], [giaBan], [ghiChu]) VALUES (N'HD260504152007', N'SP37', 2, CAST(30000 AS Decimal(18, 0)), N'')
GO
INSERT [dbo].[DanhMuc] ([maDanhMuc], [tenDanhMuc]) VALUES (N'CF', N'Cà Phê Truyền Thống')
GO
INSERT [dbo].[DanhMuc] ([maDanhMuc], [tenDanhMuc]) VALUES (N'CM', N'Cà Phê Espresso')
GO
INSERT [dbo].[DanhMuc] ([maDanhMuc], [tenDanhMuc]) VALUES (N'DX', N'Đá Xay')
GO
INSERT [dbo].[DanhMuc] ([maDanhMuc], [tenDanhMuc]) VALUES (N'MK', N'Món Ưa Chuộng')
GO
INSERT [dbo].[DanhMuc] ([maDanhMuc], [tenDanhMuc]) VALUES (N'TS', N'Trà Sữa')
GO
INSERT [dbo].[DanhMuc] ([maDanhMuc], [tenDanhMuc]) VALUES (N'TTC', N'Trà Trái Cây')
GO
INSERT [dbo].[DatBan] ([maDatBan], [maBan], [tenKhachHang], [soDienThoai], [thoiGianDat], [thoiGianNhanBan], [trangThai], [maKhuVuc]) VALUES (N'DB260502003151', N'KV01_B1', N'Lê Khãi Minh', N'12231423', CAST(N'2026-05-02T00:31:51.533' AS DateTime), CAST(N'2026-05-02T00:31:00.000' AS DateTime), 0, N'KV01')
GO
INSERT [dbo].[DatBan] ([maDatBan], [maBan], [tenKhachHang], [soDienThoai], [thoiGianDat], [thoiGianNhanBan], [trangThai], [maKhuVuc]) VALUES (N'DB260502005055', N'KV01_B1', N'Lê Khãi Hinh', N'1223142312', CAST(N'2026-05-02T00:50:55.757' AS DateTime), CAST(N'2026-05-02T00:31:00.000' AS DateTime), 0, N'KV01')
GO
INSERT [dbo].[DatBan] ([maDatBan], [maBan], [tenKhachHang], [soDienThoai], [thoiGianDat], [thoiGianNhanBan], [trangThai], [maKhuVuc]) VALUES (N'DB260502005219', N'KV01_B1', N'Lê Khãi Binh', N'122314231123', CAST(N'2026-05-02T00:52:19.780' AS DateTime), CAST(N'2026-05-02T00:31:00.000' AS DateTime), 1, N'KV01')
GO
INSERT [dbo].[DatBan] ([maDatBan], [maBan], [tenKhachHang], [soDienThoai], [thoiGianDat], [thoiGianNhanBan], [trangThai], [maKhuVuc]) VALUES (N'DB260502005224', N'KV01_B1', N'Lê Khãi Minh', N'122314234', CAST(N'2026-05-02T00:52:24.890' AS DateTime), CAST(N'2026-05-02T00:31:00.000' AS DateTime), 1, N'KV01')
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260502100213', N'NV01', N'KV01_B1', CAST(N'2026-05-02T10:02:13.147' AS DateTime), CAST(230000 AS Decimal(18, 0)), CAST(18400 AS Decimal(18, 0)), CAST(10000 AS Decimal(18, 0)), CAST(238400 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260502100850', N'NV01', N'KV01_B1', CAST(N'2026-05-02T10:08:50.067' AS DateTime), CAST(545000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(545000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260502143730', N'NV01', N'KV01_B2', CAST(N'2026-05-02T14:37:30.490' AS DateTime), CAST(400000 AS Decimal(18, 0)), CAST(20000 AS Decimal(18, 0)), CAST(12313 AS Decimal(18, 0)), CAST(407687 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260502151323', N'NV01', N'KV03_B2', CAST(N'2026-05-02T15:13:23.550' AS DateTime), CAST(285000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(285000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260502153514', N'NV01', N'KV01_B11', CAST(N'2026-05-02T15:35:14.713' AS DateTime), CAST(280000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(280000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260502153532', N'NV01', N'KV02_B7', CAST(N'2026-05-02T15:35:32.143' AS DateTime), CAST(345000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(345000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260502175925', N'NV01', N'KV01_B2', CAST(N'2026-05-02T17:59:25.040' AS DateTime), CAST(260000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(10000 AS Decimal(18, 0)), CAST(250000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260502175936', N'NV01', N'KV03_B2', CAST(N'2026-05-02T17:59:36.447' AS DateTime), CAST(290000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(10000 AS Decimal(18, 0)), CAST(280000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260502180024', N'NV01', N'KV03_B2', CAST(N'2026-05-02T18:00:24.013' AS DateTime), CAST(825000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(825000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260502214640', N'NV01', N'KV01_B11', CAST(N'2026-05-02T21:46:40.177' AS DateTime), CAST(465000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(465000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260502215147', N'NV01', N'KV02_B1', CAST(N'2026-05-02T21:51:47.400' AS DateTime), CAST(275000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(275000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260502215242', N'NV01', N'KV02_B5', CAST(N'2026-05-02T21:52:42.843' AS DateTime), CAST(160000 AS Decimal(18, 0)), CAST(16000 AS Decimal(18, 0)), CAST(100000 AS Decimal(18, 0)), CAST(76000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260504140305', N'NV01', N'KV01_B11', CAST(N'2026-05-04T14:03:05.280' AS DateTime), CAST(1545000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(1545000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260504140313', N'NV01', N'KV03_B2', CAST(N'2026-05-04T14:03:13.057' AS DateTime), CAST(395000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(395000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260504151958', N'NV01', N'KV01_B10', CAST(N'2026-05-04T15:19:58.357' AS DateTime), CAST(720000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(720000 AS Decimal(18, 0)), 0)
GO
INSERT [dbo].[HoaDon] ([maHoaDon], [maNhanVien], [maBan], [thoiGianLap], [tongTien], [thueVAT], [giamGia], [thanhTien], [trangThai]) VALUES (N'HD260504152007', N'NV01', N'KV03_B3', CAST(N'2026-05-04T15:20:07.123' AS DateTime), CAST(300000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(300000 AS Decimal(18, 0)), 0)
GO
INSERT [dbo].[KhuVuc] ([maKhuVuc], [tenKhuVuc], [phuThu]) VALUES (N'KV01', N'Tầng Trệt', CAST(0 AS Decimal(18, 0)))
GO
INSERT [dbo].[KhuVuc] ([maKhuVuc], [tenKhuVuc], [phuThu]) VALUES (N'KV02', N'Lầu 1 (Máy lạnh)', CAST(10000 AS Decimal(18, 0)))
GO
INSERT [dbo].[KhuVuc] ([maKhuVuc], [tenKhuVuc], [phuThu]) VALUES (N'KV03', N'Sân Thượng', CAST(5000 AS Decimal(18, 0)))
GO
INSERT [dbo].[NhanVien] ([maNhanVien], [hoTenNhanVien], [soCCCD], [soDienThoai], [tenDangNhap], [trangThai]) VALUES (N'NV01', N'Nguyễn Văn An', N'079203001111', N'0901111111', N'nguyenvanan', 1)
GO
INSERT [dbo].[NhanVien] ([maNhanVien], [hoTenNhanVien], [soCCCD], [soDienThoai], [tenDangNhap], [trangThai]) VALUES (N'NV02', N'Trần Thị Bích', N'079203002222', N'0902222222', N'tranthibich', 1)
GO
INSERT [dbo].[NhanVien] ([maNhanVien], [hoTenNhanVien], [soCCCD], [soDienThoai], [tenDangNhap], [trangThai]) VALUES (N'NV03', N'Lê Đình Cường', N'079203003333', N'0903333333', N'ledinhcuong', 1)
GO
INSERT [dbo].[NhanVien] ([maNhanVien], [hoTenNhanVien], [soCCCD], [soDienThoai], [tenDangNhap], [trangThai]) VALUES (N'NV04', N'Phạm Thị Dung', N'079203004444', N'0904444444', N'phamthidung', 1)
GO
INSERT [dbo].[NhanVien] ([maNhanVien], [hoTenNhanVien], [soCCCD], [soDienThoai], [tenDangNhap], [trangThai]) VALUES (N'NV05', N'Hoàng Minh Tuấn', N'079203005555', N'0905555555', N'hoangminhtuan', 1)
GO
INSERT [dbo].[NhanVien] ([maNhanVien], [hoTenNhanVien], [soCCCD], [soDienThoai], [tenDangNhap], [trangThai]) VALUES (N'NV06', N'Lê Khãi Minh', N'079203005551', N'0349445392', N'minhto', 1)
GO
INSERT [dbo].[NhanVien] ([maNhanVien], [hoTenNhanVien], [soCCCD], [soDienThoai], [tenDangNhap], [trangThai]) VALUES (N'NV07', N'Lê Anh Minh', N'079203001122', N'0901111133', N'leanhminh', 1)
GO
INSERT [dbo].[NhanVien] ([maNhanVien], [hoTenNhanVien], [soCCCD], [soDienThoai], [tenDangNhap], [trangThai]) VALUES (N'NV08', N'Tô Thanh Đạt', N'079203001100', N'0901111166', N'tothanhdat', 1)
GO
INSERT [dbo].[PhanCongCaLam] ([maNhanVien], [maCa], [ngayLam], [trangThaiDiemDanh]) VALUES (N'NV02', N'CA01', CAST(N'2026-04-29' AS Date), 0)
GO
INSERT [dbo].[PhanCongCaLam] ([maNhanVien], [maCa], [ngayLam], [trangThaiDiemDanh]) VALUES (N'NV03', N'CA02', CAST(N'2026-04-29' AS Date), 0)
GO
INSERT [dbo].[PhanCongCaLam] ([maNhanVien], [maCa], [ngayLam], [trangThaiDiemDanh]) VALUES (N'NV04', N'CA02', CAST(N'2026-04-29' AS Date), 0)
GO
INSERT [dbo].[PhanCongCaLam] ([maNhanVien], [maCa], [ngayLam], [trangThaiDiemDanh]) VALUES (N'NV05', N'CA01', CAST(N'2026-04-29' AS Date), 0)
GO
INSERT [dbo].[PhanCongCaLam] ([maNhanVien], [maCa], [ngayLam], [trangThaiDiemDanh]) VALUES (N'NV05', N'CA02', CAST(N'2026-04-29' AS Date), 0)
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP01', N'CF', N'Cà phê đen', CAST(20000 AS Decimal(18, 0)), 1, N'capheden.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP02', N'CF', N'Cà phê sữa', CAST(25000 AS Decimal(18, 0)), 1, N'caphesua.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP03', N'CF', N'Bạc xỉu', CAST(25000 AS Decimal(18, 0)), 1, N'bacxiu.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP04', N'CF', N'Cà phê cốt dừa', CAST(35000 AS Decimal(18, 0)), 1, N'caphecotdua.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP05', N'CF', N'Cà phê muối', CAST(35000 AS Decimal(18, 0)), 1, N'caphemuoi.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP06', N'CF', N'Cà phê đá', CAST(20000 AS Decimal(18, 0)), 1, N'capheda.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP07', N'CF', N'Cà phê trứng', CAST(40000 AS Decimal(18, 0)), 1, N'caphetrung.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP08', N'CF', N'Cà phê bơ', CAST(40000 AS Decimal(18, 0)), 1, N'caphebo.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP09', N'CF', N'Cà phê phin', CAST(25000 AS Decimal(18, 0)), 1, N'caphephin.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP10', N'TS', N'Hồng trà sữa', CAST(25000 AS Decimal(18, 0)), 1, N'Hồng trà sữa.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP11', N'TS', N'Trà sữa olong', CAST(30000 AS Decimal(18, 0)), 1, N'Trà sữa olong.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP12', N'TS', N'Trà sữa lài', CAST(25000 AS Decimal(18, 0)), 1, N'Trà sữa lài.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP13', N'TS', N'Trà sữa Matcha', CAST(30000 AS Decimal(18, 0)), 1, N'Trà sữa Matcha.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP14', N'TS', N'Trà sữa socola', CAST(30000 AS Decimal(18, 0)), 1, N'Trà sữa socola.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP15', N'TS', N'Trà sữa nhân sen', CAST(35000 AS Decimal(18, 0)), 1, N'Trà sữa nhân sen.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP16', N'TS', N'Trà đào sữa thạch', CAST(35000 AS Decimal(18, 0)), 1, N'Hồng trà đào sữa thạch.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP17', N'TS', N'Trà sữa ô long lài thạch', CAST(35000 AS Decimal(18, 0)), 1, N'Trà sửa ô long lài thạch.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP18', N'CM', N'Espresso', CAST(30000 AS Decimal(18, 0)), 1, N'espresso.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP19', N'CM', N'Cappuccino', CAST(40000 AS Decimal(18, 0)), 1, N'capuchino.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP20', N'CM', N'Americano', CAST(35000 AS Decimal(18, 0)), 1, N'Americano.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP21', N'CM', N'Chocolate Cream Cold Brew', CAST(45000 AS Decimal(18, 0)), 1, N'Chocolate Cream Cold Brew.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP22', N'CM', N'Com Cream latte', CAST(45000 AS Decimal(18, 0)), 1, N'Com Cream latte.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP23', N'CM', N'Asian Dolce latte', CAST(45000 AS Decimal(18, 0)), 1, N'Assian Dolce latte.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP24', N'DX', N'Matcha đá xay', CAST(45000 AS Decimal(18, 0)), 1, N'matcha-da-xay.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP25', N'DX', N'Matcha Ice Blended', CAST(45000 AS Decimal(18, 0)), 1, N'matcha_ice_blended_master.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP26', N'DX', N'Bạc hà choco', CAST(45000 AS Decimal(18, 0)), 1, N'Bạc hà choco.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP27', N'DX', N'Dâu đá xay', CAST(45000 AS Decimal(18, 0)), 1, N'Dâu đá xay.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP28', N'DX', N'Hạnh nhân choco', CAST(45000 AS Decimal(18, 0)), 1, N'Hạnh nhân choco.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP29', N'TTC', N'Trà chanh', CAST(20000 AS Decimal(18, 0)), 1, N'Trà chanh.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP30', N'TTC', N'Trà vải', CAST(25000 AS Decimal(18, 0)), 1, N'Trà vải.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP31', N'TTC', N'Trà nhãn', CAST(25000 AS Decimal(18, 0)), 1, N'Trà nhãn.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP32', N'TTC', N'Trà lựu', CAST(30000 AS Decimal(18, 0)), 1, N'Trà lựu.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP33', N'TTC', N'Trà bưởi mật ong', CAST(30000 AS Decimal(18, 0)), 1, N'Trà bưởi mật ong.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP34', N'TTC', N'Trà thơm hạt đác', CAST(35000 AS Decimal(18, 0)), 1, N'Trà thơm hạt đác.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP35', N'TTC', N'Trà sen vàng', CAST(35000 AS Decimal(18, 0)), 1, N'Trà sen vàng.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP36', N'TTC', N'Trà tắc', CAST(20000 AS Decimal(18, 0)), 1, N'Trà tắc.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP37', N'TTC', N'Trà đào cam sả', CAST(30000 AS Decimal(18, 0)), 1, N'tradaocamxa.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP38', N'TTC', N'Trà dâu', CAST(30000 AS Decimal(18, 0)), 1, N'Trà dâu.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP39', N'MK', N'Milo dầm', CAST(30000 AS Decimal(18, 0)), 1, N'milodam.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP40', N'MK', N'Nước chanh tươi', CAST(20000 AS Decimal(18, 0)), 1, N'trachanh.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP41', N'MK', N'Trà đào cam xả', CAST(30000 AS Decimal(18, 0)), 1, N'tradaocamxa.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP42', N'MK', N'Đá xay 3 tầng', CAST(45000 AS Decimal(18, 0)), 1, N'Đá xay 3 tầng.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP43', N'MK', N'Sữa tươi socola', CAST(30000 AS Decimal(18, 0)), 1, N'Sữa tươi socola.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP44', N'MK', N'Sữa tươi caramel', CAST(30000 AS Decimal(18, 0)), 1, N'Sữa tuoi caramel.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP45', N'MK', N'Trà sữa trân châu đường đen', CAST(35000 AS Decimal(18, 0)), 1, N'Trà sữa trân châu đường đen.jpg')
GO
INSERT [dbo].[SanPham] ([maSanPham], [maDanhMuc], [tenSanPham], [giaBan], [trangThai], [hinhAnh]) VALUES (N'SP46', N'MK', N'Soda trái cây', CAST(30000 AS Decimal(18, 0)), 1, N'Soda trái cây.jpg')
GO
INSERT [dbo].[TaiKhoan] ([tenDangNhap], [matKhau], [trangThai], [maVaiTro]) VALUES (N'hoangminhtuan', N'123456', 1, N'ThuNgan')
GO
INSERT [dbo].[TaiKhoan] ([tenDangNhap], [matKhau], [trangThai], [maVaiTro]) VALUES (N'leanhminh', N'123456', 1, N'ThuNgan')
GO
INSERT [dbo].[TaiKhoan] ([tenDangNhap], [matKhau], [trangThai], [maVaiTro]) VALUES (N'ledinhcuong', N'123456', 1, N'ThuNgan')
GO
INSERT [dbo].[TaiKhoan] ([tenDangNhap], [matKhau], [trangThai], [maVaiTro]) VALUES (N'minhto', N'123456', 1, N'QuanLy')
GO
INSERT [dbo].[TaiKhoan] ([tenDangNhap], [matKhau], [trangThai], [maVaiTro]) VALUES (N'nguyenvanan', N'123456', 1, N'QuanLy')
GO
INSERT [dbo].[TaiKhoan] ([tenDangNhap], [matKhau], [trangThai], [maVaiTro]) VALUES (N'phamthidung', N'123456', 1, N'ThuNgan')
GO
INSERT [dbo].[TaiKhoan] ([tenDangNhap], [matKhau], [trangThai], [maVaiTro]) VALUES (N'tothanhdat', N'123456', 1, N'ThuNgan')
GO
INSERT [dbo].[TaiKhoan] ([tenDangNhap], [matKhau], [trangThai], [maVaiTro]) VALUES (N'tranthibich', N'123456', 1, N'QuanLy')
GO
INSERT [dbo].[VaiTro] ([maVaiTro], [tenVaiTro]) VALUES (N'QuanLy', N'Quản Lý')
GO
INSERT [dbo].[VaiTro] ([maVaiTro], [tenVaiTro]) VALUES (N'ThuNgan', N'Thu Ngân')
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__NhanVien__59267D4A36E827A4]    Script Date: 5/4/2026 10:45:59 PM ******/
ALTER TABLE [dbo].[NhanVien] ADD UNIQUE NONCLUSTERED 
(
	[tenDangNhap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__NhanVien__59267D4A954B83C3]    Script Date: 5/4/2026 10:45:59 PM ******/
ALTER TABLE [dbo].[NhanVien] ADD UNIQUE NONCLUSTERED 
(
	[tenDangNhap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__NhanVien__C84E607260CB1017]    Script Date: 5/4/2026 10:45:59 PM ******/
ALTER TABLE [dbo].[NhanVien] ADD UNIQUE NONCLUSTERED 
(
	[soCCCD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__NhanVien__C84E60726E6030D8]    Script Date: 5/4/2026 10:45:59 PM ******/
ALTER TABLE [dbo].[NhanVien] ADD UNIQUE NONCLUSTERED 
(
	[soCCCD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Ban] ADD  DEFAULT (N'Trống') FOR [trangThai]
GO
ALTER TABLE [dbo].[CaLamViec] ADD  DEFAULT ((0)) FOR [tienMatDauCa]
GO
ALTER TABLE [dbo].[CaLamViec] ADD  DEFAULT ((0)) FOR [tongDoanhThuTrongCa]
GO
ALTER TABLE [dbo].[HoaDon] ADD  DEFAULT (getdate()) FOR [thoiGianLap]
GO
ALTER TABLE [dbo].[HoaDon] ADD  DEFAULT ((0)) FOR [tongTien]
GO
ALTER TABLE [dbo].[HoaDon] ADD  DEFAULT ((0)) FOR [thueVAT]
GO
ALTER TABLE [dbo].[HoaDon] ADD  DEFAULT ((0)) FOR [giamGia]
GO
ALTER TABLE [dbo].[HoaDon] ADD  DEFAULT ((0)) FOR [thanhTien]
GO
ALTER TABLE [dbo].[HoaDon] ADD  DEFAULT ((0)) FOR [trangThai]
GO
ALTER TABLE [dbo].[KhuVuc] ADD  DEFAULT ((0)) FOR [phuThu]
GO
ALTER TABLE [dbo].[NhanVien] ADD  DEFAULT ((1)) FOR [trangThai]
GO
ALTER TABLE [dbo].[PhanCongCaLam] ADD  DEFAULT ((0)) FOR [trangThaiDiemDanh]
GO
ALTER TABLE [dbo].[SanPham] ADD  DEFAULT ((1)) FOR [trangThai]
GO
ALTER TABLE [dbo].[TaiKhoan] ADD  DEFAULT ((1)) FOR [trangThai]
GO
ALTER TABLE [dbo].[Ban]  WITH CHECK ADD FOREIGN KEY([maKhuVuc])
REFERENCES [dbo].[KhuVuc] ([maKhuVuc])
GO
ALTER TABLE [dbo].[Ban]  WITH CHECK ADD FOREIGN KEY([maKhuVuc])
REFERENCES [dbo].[KhuVuc] ([maKhuVuc])
GO
ALTER TABLE [dbo].[ChiTietDatBan]  WITH CHECK ADD FOREIGN KEY([maDatBan])
REFERENCES [dbo].[DatBan] ([maDatBan])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ChiTietDatBan]  WITH CHECK ADD FOREIGN KEY([maSanPham])
REFERENCES [dbo].[SanPham] ([maSanPham])
GO
ALTER TABLE [dbo].[ChiTietDatBan]  WITH CHECK ADD FOREIGN KEY([maSanPham])
REFERENCES [dbo].[SanPham] ([maSanPham])
GO
ALTER TABLE [dbo].[ChiTietHoaDon]  WITH CHECK ADD FOREIGN KEY([maHoaDon])
REFERENCES [dbo].[HoaDon] ([maHoaDon])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ChiTietHoaDon]  WITH CHECK ADD FOREIGN KEY([maSanPham])
REFERENCES [dbo].[SanPham] ([maSanPham])
GO
ALTER TABLE [dbo].[ChiTietHoaDon]  WITH CHECK ADD FOREIGN KEY([maSanPham])
REFERENCES [dbo].[SanPham] ([maSanPham])
GO
ALTER TABLE [dbo].[DatBan]  WITH CHECK ADD FOREIGN KEY([maBan])
REFERENCES [dbo].[Ban] ([maBan])
GO
ALTER TABLE [dbo].[DatBan]  WITH CHECK ADD FOREIGN KEY([maBan])
REFERENCES [dbo].[Ban] ([maBan])
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD FOREIGN KEY([maBan])
REFERENCES [dbo].[Ban] ([maBan])
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD FOREIGN KEY([maBan])
REFERENCES [dbo].[Ban] ([maBan])
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD FOREIGN KEY([maNhanVien])
REFERENCES [dbo].[NhanVien] ([maNhanVien])
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD FOREIGN KEY([maNhanVien])
REFERENCES [dbo].[NhanVien] ([maNhanVien])
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD FOREIGN KEY([tenDangNhap])
REFERENCES [dbo].[TaiKhoan] ([tenDangNhap])
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD FOREIGN KEY([tenDangNhap])
REFERENCES [dbo].[TaiKhoan] ([tenDangNhap])
GO
ALTER TABLE [dbo].[PhanCongCaLam]  WITH CHECK ADD FOREIGN KEY([maNhanVien])
REFERENCES [dbo].[NhanVien] ([maNhanVien])
GO
ALTER TABLE [dbo].[PhanCongCaLam]  WITH CHECK ADD FOREIGN KEY([maNhanVien])
REFERENCES [dbo].[NhanVien] ([maNhanVien])
GO
ALTER TABLE [dbo].[PhanCongCaLam]  WITH CHECK ADD FOREIGN KEY([maCa])
REFERENCES [dbo].[CaLamViec] ([maCa])
GO
ALTER TABLE [dbo].[PhanCongCaLam]  WITH CHECK ADD FOREIGN KEY([maCa])
REFERENCES [dbo].[CaLamViec] ([maCa])
GO
ALTER TABLE [dbo].[SanPham]  WITH CHECK ADD FOREIGN KEY([maDanhMuc])
REFERENCES [dbo].[DanhMuc] ([maDanhMuc])
GO
ALTER TABLE [dbo].[SanPham]  WITH CHECK ADD FOREIGN KEY([maDanhMuc])
REFERENCES [dbo].[DanhMuc] ([maDanhMuc])
GO
ALTER TABLE [dbo].[TaiKhoan]  WITH CHECK ADD FOREIGN KEY([maVaiTro])
REFERENCES [dbo].[VaiTro] ([maVaiTro])
GO
ALTER TABLE [dbo].[TaiKhoan]  WITH CHECK ADD FOREIGN KEY([maVaiTro])
REFERENCES [dbo].[VaiTro] ([maVaiTro])
GO
ALTER TABLE [dbo].[ChiTietDatBan]  WITH CHECK ADD CHECK  (([soLuong]>(0)))
GO
ALTER TABLE [dbo].[ChiTietDatBan]  WITH CHECK ADD CHECK  (([soLuong]>(0)))
GO
ALTER TABLE [dbo].[ChiTietHoaDon]  WITH CHECK ADD CHECK  (([soLuong]>(0)))
GO
ALTER TABLE [dbo].[ChiTietHoaDon]  WITH CHECK ADD CHECK  (([soLuong]>(0)))
GO
