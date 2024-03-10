export interface ReferrerPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
