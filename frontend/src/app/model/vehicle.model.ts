export interface Vehicle   {

  id: number;
  licensePlate: String;
  vehicleModel: {
    modelName: string;
    constructionYear: number;
    imgURL: String;
    manufacturer: string;
  }
}
