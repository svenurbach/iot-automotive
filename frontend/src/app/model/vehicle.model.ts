export interface Vehicle   {

  id: number;
  licensePlate: string;
  yearOfConstruction: number;
  vehicleModel: {
    modelName: string;
    imgURL: string;
    manufacturer: string;
  }
}
