const api = async (url, options={}) => {
  const response = await fetch(url, {
    headers: {'Content-Type':'application/json', ...(options.headers||{})},
    ...options
  });
  const data = response.status === 204 ? null : await response.json().catch(()=>null);
  if (!response.ok) throw new Error(data?.error || `Request failed (${response.status})`);
  return data;
};
const money = value => new Intl.NumberFormat('en-IN',{style:'currency',currency:'INR'}).format(value ?? 0);
const safe = value => String(value ?? '').replace(/[&<>"']/g, ch => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[ch]));
const showMessage = (id, message, error=false) => {
  const el=document.getElementById(id); el.textContent=message; el.className='message'+(error?' error':'');
};

document.getElementById('customerForm').addEventListener('submit', async event => {
  event.preventDefault();
  const form=Object.fromEntries(new FormData(event.currentTarget));
  try {
    await api('/api/customers',{method:'POST',body:JSON.stringify(form)});
    showMessage('customerMessage','Customer saved successfully.');
    event.currentTarget.reset(); await loadCustomers();
  } catch(err){showMessage('customerMessage',err.message,true);}
});
document.getElementById('billForm').addEventListener('submit', async event => {
  event.preventDefault();
  const raw=Object.fromEntries(new FormData(event.currentTarget));
  const payload={...raw,previousReading:Number(raw.previousReading),currentReading:Number(raw.currentReading),period:raw.period};
  if(payload.currentReading<payload.previousReading){showMessage('billMessage','Current reading must be greater than or equal to previous reading.',true);return;}
  try {
    const bill=await api('/api/bills',{method:'POST',body:JSON.stringify(payload)});
    showMessage('billMessage',`Bill saved: ${bill.units} kWh — ${money(bill.amount)}.`);
    await loadBills();
  } catch(err){showMessage('billMessage',err.message,true);}
});
async function loadCustomers(){
  try {
    const customers=await api('/api/customers');
    document.getElementById('customers').innerHTML=customers.map(c=>`<tr><td>${safe(c.customerCode)}</td><td>${safe(c.name)}</td><td>${safe(c.mobile)}</td><td>${safe(c.connectionType)}</td><td>${safe(c.meterNumber)}</td></tr>`).join('') || '<tr><td colspan="5">No customers yet.</td></tr>';
  } catch(err){showMessage('customerMessage',err.message,true);}
}
async function loadBills(){
  try {
    const bills=await api('/api/bills');
    document.getElementById('bills').innerHTML=bills.map(b=>`<tr><td>${safe(b.customer?.customerCode || '')}</td><td>${safe(b.period)}</td><td>${b.units}</td><td>${money(b.amount)}</td><td>${safe(b.dueDate)}</td><td>${safe(b.status)}</td><td>${b.status==='PAID'?'Paid':`<button class="secondary" onclick="markPaid(${b.id})">Mark paid</button>`}</td></tr>`).join('') || '<tr><td colspan="7">No bills yet.</td></tr>';
  } catch(err){showMessage('billMessage',err.message,true);}
}
async function markPaid(id){
  try {await api(`/api/bills/${id}/pay`,{method:'PATCH'});await loadBills();}
  catch(err){showMessage('billMessage',err.message,true);}
}
document.getElementById('refreshCustomers').addEventListener('click',loadCustomers);
document.getElementById('refreshBills').addEventListener('click',loadBills);
loadCustomers();loadBills();
