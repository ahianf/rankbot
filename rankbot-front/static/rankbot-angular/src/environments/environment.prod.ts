export const environment = {
  production: true,
  authorize_uri: 'https://rankmachine.me/auth/oauth2/authorize?',
  client_id : 'client',
  redirect_uri: 'https://rankmachine.me/authorized',
  scope: 'openid profile',
  response_type: 'code',
  response_mode: 'form_post',
  code_challenge_method: 'S256',
  token_url: 'https://rankmachine.me/auth/oauth2/token',
  grant_type: 'authorization_code',
  resource_url: 'https://rankmachine.me/api/v2/',
  api_v1: 'http://localhost:8080/api/',
  logout_url: 'https://rankmachine.me/auth/logout',
  secret_pkce: 'secret'
};
