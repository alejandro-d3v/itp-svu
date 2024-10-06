/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import ArchivoAdjuntoService from './archivo-adjunto.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { ArchivoAdjunto } from '@/shared/model/archivo-adjunto.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('ArchivoAdjunto Service', () => {
    let service: ArchivoAdjuntoService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new ArchivoAdjuntoService();
      currentDate = new Date();
      elemDefault = new ArchivoAdjunto('ABC', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = { fechaSubida: dayjs(currentDate).format(DATE_TIME_FORMAT), ...elemDefault };
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find('ABC').then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a ArchivoAdjunto', async () => {
        const returnedFromService = { id: 'ABC', fechaSubida: dayjs(currentDate).format(DATE_TIME_FORMAT), ...elemDefault };
        const expected = { fechaSubida: currentDate, ...returnedFromService };

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a ArchivoAdjunto', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a ArchivoAdjunto', async () => {
        const returnedFromService = {
          nombre: 'BBBBBB',
          tipo: 'BBBBBB',
          urlArchivo: 'BBBBBB',
          fechaSubida: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };

        const expected = { fechaSubida: currentDate, ...returnedFromService };
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a ArchivoAdjunto', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a ArchivoAdjunto', async () => {
        const patchObject = { nombre: 'BBBBBB', ...new ArchivoAdjunto() };
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = { fechaSubida: currentDate, ...returnedFromService };
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a ArchivoAdjunto', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of ArchivoAdjunto', async () => {
        const returnedFromService = {
          nombre: 'BBBBBB',
          tipo: 'BBBBBB',
          urlArchivo: 'BBBBBB',
          fechaSubida: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };
        const expected = { fechaSubida: currentDate, ...returnedFromService };
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of ArchivoAdjunto', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a ArchivoAdjunto', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a ArchivoAdjunto', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
