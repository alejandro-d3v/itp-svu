import { type IPqrs } from '@/shared/model/pqrs.model';
import { type IRespuesta } from '@/shared/model/respuesta.model';

export interface IArchivoAdjunto {
  id?: string;
  nombre?: string;
  tipo?: string;
  urlArchivo?: string | null;
  fechaSubida?: Date;
  pqrs?: IPqrs | null;
  respuesta?: IRespuesta | null;
  file?: File | null;
}

export class ArchivoAdjunto implements IArchivoAdjunto {
  constructor(
    public id?: string,
    public nombre?: string,
    public tipo?: string,
    public urlArchivo?: string | null,
    public fechaSubida?: Date,
    public pqrs?: IPqrs | null,
    public respuesta?: IRespuesta | null,
  ) {}
}
