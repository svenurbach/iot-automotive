export interface Insurance {
  id: number;
  begin: Date;
  contractDistance: number;
  contractPrice: number;
  deductible: number;
  policyNumber: string;
  insurance: {
    id: number;
    insuranceName: string;
    insuranceType: string;
    insuranceCompany: {
      id: number;
      companyName: string;
    }
  }
}