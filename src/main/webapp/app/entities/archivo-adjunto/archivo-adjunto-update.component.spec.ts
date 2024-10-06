/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import ArchivoAdjuntoUpdate from './archivo-adjunto-update.vue';
import ArchivoAdjuntoService from './archivo-adjunto.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import PqrsService from '@/entities/pqrs/pqrs.service';
import RespuestaService from '@/entities/respuesta/respuesta.service';

type ArchivoAdjuntoUpdateComponentType = InstanceType<typeof ArchivoAdjuntoUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const archivoAdjuntoSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ArchivoAdjuntoUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ArchivoAdjunto Management Update Component', () => {
    let comp: ArchivoAdjuntoUpdateComponentType;
    let archivoAdjuntoServiceStub: SinonStubbedInstance<ArchivoAdjuntoService>;

    beforeEach(() => {
      route = {};
      archivoAdjuntoServiceStub = sinon.createStubInstance<ArchivoAdjuntoService>(ArchivoAdjuntoService);
      archivoAdjuntoServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          archivoAdjuntoService: () => archivoAdjuntoServiceStub,
          pqrsService: () =>
            sinon.createStubInstance<PqrsService>(PqrsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          respuestaService: () =>
            sinon.createStubInstance<RespuestaService>(RespuestaService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(ArchivoAdjuntoUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ArchivoAdjuntoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.archivoAdjunto = archivoAdjuntoSample;
        archivoAdjuntoServiceStub.update.resolves(archivoAdjuntoSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(archivoAdjuntoServiceStub.update.calledWith(archivoAdjuntoSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        archivoAdjuntoServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ArchivoAdjuntoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.archivoAdjunto = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(archivoAdjuntoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        archivoAdjuntoServiceStub.find.resolves(archivoAdjuntoSample);
        archivoAdjuntoServiceStub.retrieve.resolves([archivoAdjuntoSample]);

        // WHEN
        route = {
          params: {
            archivoAdjuntoId: `${archivoAdjuntoSample.id}`,
          },
        };
        const wrapper = shallowMount(ArchivoAdjuntoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.archivoAdjunto).toMatchObject(archivoAdjuntoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        archivoAdjuntoServiceStub.find.resolves(archivoAdjuntoSample);
        const wrapper = shallowMount(ArchivoAdjuntoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
