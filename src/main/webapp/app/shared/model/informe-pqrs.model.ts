import { type IOficina } from '@/shared/model/oficina.model';

export interface IInformePqrs {
  id?: string;
  fechaInicio?: Date;
  fechaFin?: Date;
  totalPqrs?: number;
  totalResueltas?: number;
  totalPendientes?: number;
  oficina?: IOficina | null;
}

export class InformePqrs implements IInformePqrs {
  constructor(
    public id?: string,
    public fechaInicio?: Date,
    public fechaFin?: Date,
    public totalPqrs?: number,
    public totalResueltas?: number,
    public totalPendientes?: number,
    public oficina?: IOficina | null,
  ) {}
}
