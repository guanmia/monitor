import request from '@/utils/request'

export function meta(route, length) {
  let url;
  if (length) {
    url = '/region-center/' + route + '/metadata-update?length=' + length
  } else {
    url = '/region-center/' + route + '/metadata'
  }
  return request({
    url: url,
    method: 'post',
  })
}

export function gate(route, length, para) {
  let url;
  if (length) {
    url = '/region-center/' + route + '/gateway-update?gateway_id=' + para + '&length=' + length
  } else {
    url = '/region-center/' + route + '/gateway?gateway_id=' + para
  }
  return request({
    url: url,
    method: 'post',
  })
}

export function moni(length) {
  let url;
  if (length && isNaN(length) == false) {
    url = '/region/monitor-update?length=' + length
  } else {
    url = '/region/monitor'
  }
  return request({
    url: url,
    method: 'post',
  })
}

export function choice() {
  return request({
    url: '/admin/region/metadata/list',
    method: 'post',
  })
}

export function stat() {
  return request({
    url: '/admin/client/stat/maps',
    method: 'post',
  })
}

export function files(data) {
  return request({
    url: '/admin/client/status/nodefile',
    method: 'post',
    data
  })
}

export function maps(data) {
  return request({
    url: '/region-center/' + data + '/maps',
    method: 'post',
  })
}

export function topo(data) {
  return request({
    url: '/admin/client/sgwstatic/gatewaymessage',
    method: 'post',
    data: {
      region_id: data
    }
  })
}

export function recalcm(data) {
  return request({
    url: '/region-center/' + data + '/metadata/recalc',
    method: 'post',
  })
}

export function recalcg(data) {
  return request({
    url: '/region-center/' + data + '/gateway/recalc',
    method: 'post',
  })
}