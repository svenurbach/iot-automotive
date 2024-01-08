
export class Picture   { 
	declare public id:number;
		
		name!: string;
		path!: string;
		description!: string;
		
		createdAt!: Date;
		
		categories!: string [];
		categoriesDisplayExpr!: any;
		
		pictureData!: any;

		public toString(): string {
			return this.id + ', ' + this.name.toString();
		}
	}
