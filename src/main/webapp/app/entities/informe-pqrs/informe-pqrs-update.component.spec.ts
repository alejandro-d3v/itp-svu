/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import InformePqrsUpdate from './informe-pqrs-update.vue';
import InformePqrsService from './informe-pqrs.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import OficinaService from '@/entities/oficina/oficina.service';

type InformePqrsUpdateComponentType = InstanceType<typeof InformePqrsUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const informePqrsSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<InformePqrsUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('InformePqrs Management Update Component', () => {
    let comp: InformePqrsUpdateComponentType;
    let informePqrsServiceStub: SinonStubbedInstance<InformePqrsService>;

    beforeEach(() => {
      route = {};
      informePqrsServiceStub = sinon.createStubInstance<InformePqrsService>(InformePqrsService);
      informePqrsServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          informePqrsService: () => informePqrsServiceStub,
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
        const wrapper = shallowMount(InformePqrsUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(InformePqrsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.informePqrs = informePqrsSample;
        informePqrsServiceStub.update.resolves(informePqrsSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(informePqrsServiceStub.update.calledWith(informePqrsSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        informePqrsServiceStub.create.resolves(entity);
        const wrapper = shallowMount(InformePqrsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.informePqrs = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(informePqrsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        informePqrsServiceStub.find.resolves(informePqrsSample);
        informePqrsServiceStub.retrieve.resolves([informePqrsSample]);

        // WHEN
        route = {
          params: {
            informePqrsId: `${informePqrsSample.id}`,
          },
        };
        const wrapper = shallowMount(InformePqrsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.informePqrs).toMatchObject(informePqrsSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        informePqrsServiceStub.find.resolves(informePqrsSample);
        const wrapper = shallowMount(InformePqrsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
