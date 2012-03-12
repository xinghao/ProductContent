package com.airarena.hibernate.util;

import com.airarena.products.model.*;

public class InitDB {
	
	// should only be run when we create database
	public static void insertInitData() {
		MyEntityManager s = new MyEntityManager();
		s.newModel(new Provider("aws"));
		s.newModel(new ProductAttributeMetaKey("Artist".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("AspectRatio".toLowerCase(), "The ratio of an item's length to its width."));
		s.newModel(new ProductAttributeMetaKey("AudienceRating".toLowerCase(), "Audience rating for a movie. The rating suggests the age for which the movie is appropriate. The rating format varies by locale. "));
		s.newModel(new ProductAttributeMetaKey("AudioFormat".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Author".toLowerCase(), null));
		//s.newModel(new ProductAttribute("Binding", null));
		s.newModel(new ProductAttributeMetaKey("Brand".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("CEROAgeRating".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ClothingSize".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Color".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Creator".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Department".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Director".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Edition".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("EISBN".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("EpisodeSequence".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ESRBAgeRating".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Feature".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Format".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Genre".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("HardwarePlatform".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("HazardousMaterialType".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("IsAdultProduct".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("IsAutographed".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ISBN".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("IssuesPerYear".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ItemDimensions".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ItemPartNumber".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Label".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Languages".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("LegalDisclaimer".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ListPrice".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("MagazineType".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Manufacturer".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ManufacturerMaximumAge".toLowerCase(), "Defines the maximum age in months the user should be to enjoy the use of the item. For example, for a toy targeted at kids from ages 2 to 4, 4 would be the value for the ManufacturerMaximumAge. "));
		s.newModel(new ProductAttributeMetaKey("ManufacturerMinimumAge".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ManufacturerPartsWarrantyDescription".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("MediaType".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Model".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ModelYear".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("MPN".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("NumberOfDiscs".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("NumberOfIssues".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("NumberOfItems".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("NumberOfPages".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("NumberOfTracks".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("OperatingSystem".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("PackageDimensions".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("PackageQuantity".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("PartNumber".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("PictureFormat".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Platform".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ProductGroup".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ProductTypeName".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ProductTypeSubcategory".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("PublicationDate".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Publisher".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("RegionCode".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ReleaseDate".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("RunningTime".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("SeikodoProductCode".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("ShoeSize".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Size".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Studio".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("SubscriptionLength".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Title".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("TrackSequence".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("UPC".toLowerCase(), null));
		s.newModel(new ProductAttributeMetaKey("Warranty".toLowerCase(), null));		
	}

}
