export interface ReferrerDetails {
  /**
   * The install referrer passed to the store when installing the app, if any.
   */
  referrerUrl?: string;
  /**
   * The install referrer click time.
   */
  referrerClickTime?: number;
  /**
   * The app install time.
   */
  appInstallTime?: number;
  /**
   * If the instant experience was launched.
   */
  instantExperienceLaunched?: boolean;
}

export interface ReferrerPlugin {
  getReferrerDetails(): Promise<ReferrerDetails>;
}
