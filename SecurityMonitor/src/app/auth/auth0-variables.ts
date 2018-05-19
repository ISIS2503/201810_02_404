interface AuthConfig {
  clientID: string;
  domain: string;
  callbackURL: string;
}

export const AUTH_CONFIG: AuthConfig = {
  clientID: 'SvR6fwyOvILrnhnAEv48MgiTIxquEsm6',
  domain: 'isis2503-404.auth0.com',
  callbackURL: 'http://localhost:4200/callback'
};
