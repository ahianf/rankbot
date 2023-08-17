export const environment = {
  production: true,
  authorize_uri: 'https://auth.rankmachine.me/oauth2/authorize?',
  client_id : 'client',
  redirect_uri: 'https://rankmachine.me/authorized',
  scope: 'openid profile',
  response_type: 'code',
  response_mode: 'form_post',
  code_challenge_method: 'S256',
  token_url: 'https://auth.rankmachine.me/oauth2/token',
  grant_type: 'authorization_code',
  resource_url: 'https://rankmachine.me/api/v2/',
  api_v1: 'https://rankmachine.me/api/',
  logout_url: 'https://auth.rankmachine.me/logout',
  secret_pkce: 'secret'
};
