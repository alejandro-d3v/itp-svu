/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import PqrsUpdate from './pqrs-update.vue';
import PqrsService from './pqrs.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import OficinaService from '@/entities/oficina/oficina.service';

type PqrsUpdateComponentType = InstanceType<typeof PqrsUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const pqrsSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PqrsUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Pqrs Management Update Component', () => {
    let comp: PqrsUpdateComponentType;
    let pqrsServiceStub: SinonStubbedInstance<PqrsService>;

    beforeEach(() => {
      route = {};
      pqrsServiceStub = sinon.createStubInstance<PqrsService>(PqrsService);
      pqrsServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          pqrsService: () => pqrsServiceStub,
          oficinaService: () =>
            sinon.createStubInstance<OficinaService>(OficinaService, {
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
        const wrapper = shallowMount(PqrsUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(PqrsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.pqrs = pqrsSample;
        pqrsServiceStub.update.resolves(pqrsSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(pqrsServiceStub.update.calledWith(pqrsSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        pqrsServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PqrsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.pqrs = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(pqrsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        pqrsServiceStub.find.resolves(pqrsSample);
        pqrsServiceStub.retrieve.resolves([pqrsSample]);

        // WHEN
        route = {
          params: {
            pqrsId: `${pqrsSample.id}`,
          },
        };
        const wrapper = shallowMount(PqrsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.pqrs).toMatchObject(pqrsSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        pqrsServiceStub.find.resolves(pqrsSample);
        const wrapper = shallowMount(PqrsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
