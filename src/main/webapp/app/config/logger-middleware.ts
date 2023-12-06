/* eslint no-console: off */
export default () => (next: (arg0: any) => any) => (action: { type: any; payload: any; meta: any; error: any }) => {
  if (DEVELOPMENT) {
    const { type, payload, meta, error } = action;

    console.groupCollapsed(type);
    console.log('Payload:', payload);
    if (error) {
      console.log('Error:', error);
    }
    console.log('Meta:', meta);
    console.groupEnd();
  }

  return next(action);
};
