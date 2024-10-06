/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Oficina from './oficina.vue';
import OficinaService from './oficina.service';
import AlertService from '@/shared/alert/alert.service';

type OficinaComponentType = InstanceType<typeof Oficina>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Oficina Management Component', () => {
    let oficinaServiceStub: SinonStubbedInstance<OficinaService>;
    let mountOptions: MountingOptions<OficinaComponentType>['global'];

    beforeEach(() => {
      oficinaServiceStub = sinon.createStubInstance<OficinaService>(OficinaService);
      oficinaServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          oficinaService: () => oficinaServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        oficinaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(Oficina, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(oficinaServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.oficinas[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: OficinaComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Oficina, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        oficinaServiceStub.retrieve.reset();
        oficinaServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        oficinaServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeOficina();
        await comp.$nextTick(); // clear components

        // THEN
        expect(oficinaServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(oficinaServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
