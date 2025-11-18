import { reactive } from 'vue';

const state = reactive({ toasts: [] });
let id = 0;

function push(msg, type = 'info', timeout = 3000) {
  const t = { id: ++id, msg, type };
  state.toasts.push(t);
  setTimeout(() => {
    const i = state.toasts.findIndex(x => x.id === t.id);
    if (i !== -1) state.toasts.splice(i, 1);
  }, timeout);
}

export function useToast() {
  return {
    toasts: state.toasts,
    info: (m,t)=>push(m,'info',t),
    success: (m,t)=>push(m,'success',t),
    error: (m,t)=>push(m,'error',t)
  };
}
