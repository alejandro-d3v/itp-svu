/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import ArchivoAdjunto from './archivo-adjunto.vue';
import ArchivoAdjuntoService from './archivo-adjunto.service';
import AlertService from '@/shared/alert/alert.service';

type ArchivoAdjuntoComponentType = InstanceType<typeof ArchivoAdjunto>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('ArchivoAdjunto Management Component', () => {
    let archivoAdjuntoServiceStub: SinonStubbedInstance<ArchivoAdjuntoService>;
    let mountOptions: MountingOptions<ArchivoAdjuntoComponentType>['global'];

    beforeEach(() => {
      archivoAdjuntoServiceStub = sinon.createStubInstance<ArchivoAdjuntoService>(ArchivoAdjuntoService);
      archivoAdjuntoServiceStub.retrieve.resolves({ headers: {} });

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
          archivoAdjuntoService: () => archivoAdjuntoServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        archivoAdjuntoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(ArchivoAdjunto, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(archivoAdjuntoServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.archivoAdjuntos[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: ArchivoAdjuntoComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(ArchivoAdjunto, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        archivoAdjuntoServiceStub.retrieve.reset();
        archivoAdjuntoServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        archivoAdjuntoServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeArchivoAdjunto();
        await comp.$nextTick(); // clear components

        // THEN
        expect(archivoAdjuntoServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(archivoAdjuntoServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
