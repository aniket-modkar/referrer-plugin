import { WebPlugin } from '@capacitor/core';

import type { ReferrerPlugin } from './definitions';

export class ReferrerWeb extends WebPlugin implements ReferrerPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
