export interface Vehicle   {

  id: number;
  licensePlate: string;
  vehicleModel: {
    modelName: string;
    constructionYear: number;
    imgURL: string;
    manufacturer: string;
  }
}
