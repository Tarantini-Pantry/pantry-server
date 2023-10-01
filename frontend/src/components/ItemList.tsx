import {Col, ListGroup} from 'react-bootstrap';
import {ItemCard} from './ItemCard';
import {CreateItemCard} from './CreateItemCard';
import {useEffect, useState} from 'react';
import ItemsService from '../ItemsService';
import {AxiosResponse} from "axios";

export function ItemList() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = () => {
    ItemsService.getAll()
      .then((response: AxiosResponse) => {
        setLoading(false);
        setItems(response.data);
        console.log(response.data);
      })
      .catch((e: Error) => {
        setLoading(false);
        console.log(e);
      });
  };

  const refreshList = () => {
    fetchItems();
  };

  return (
    <ListGroup data-bs-theme="dark" as={Col} md={4}>
      {items?.map((item, i) => (
        <ItemCard key={i} item={item}/>
      ))}
      {loading ? (<br/>) : (<CreateItemCard onCreate={refreshList}/>)}
    </ListGroup>
  );
}
