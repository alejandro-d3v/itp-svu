export interface IResponsePag<Data> extends IPag {
  data: Data[];
  page: number;
  perPage: number;
  total: number;
}

export interface IPag {
  page: number;
  perPage: number;
  total: number;
}

export interface IParamsPag {
  order?: 'ASC' | 'DESC';
  page?: number;
  perPage?: number;
  search?: string;
}
