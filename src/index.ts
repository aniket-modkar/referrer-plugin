import { registerPlugin } from '@capacitor/core';

import type { ReferrerPlugin } from './definitions';

const Referrer = registerPlugin<ReferrerPlugin>('Referrer', {
  web: () => import('./web').then(m => new m.ReferrerWeb()),
});

export * from './definitions';
export { Referrer };
