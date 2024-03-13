export interface Insurance {
  id: number;
  begin: Date;
  contractDistance: number;
  contractPrice: number;
  deductible: number;
  policyNumber: string;
  policyholder: {
    id: number;
    name: string;
    dateOfBirth: Date;
  }
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