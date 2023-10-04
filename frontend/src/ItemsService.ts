import http from 'http-common';
import {Item} from './types/Item';

const getAll = () => {
  return http.get<Array<Item>>('/items', {});
};

const get = (id: number) => {
  return http.get<Item>(`/items/${id}`);
};

const create = (data: Item) => {
  return http.post<Item>('/items', data, {
    headers: {
      'Content-type': 'application/json',
    }
  });
};

const remove = (id: number) => {
  return http.delete<null>(`/items/${id}`);
};

const removeAll = () => {
  return http.delete<null>(`/items`);
};

const ItemsService = {
  getAll,
  get,
  create,
  remove,
  removeAll,
};

export default ItemsService;
