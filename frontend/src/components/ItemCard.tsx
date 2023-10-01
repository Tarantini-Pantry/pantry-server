import {Card} from 'react-bootstrap';
import {Item} from '../types/Item';

interface ItemProps {
  item: Item;
}

export function ItemCard({item}: ItemProps) {
  return (
    <Card data-bs-theme="dark" style={{width: '18rem'}}>
      <Card.Img variant="top" src="https://placehold.co/180x100"/>
      <Card.Body className="rounded mx-2">
        <Card.Title>{item.name}</Card.Title>
        <Card.Text>{item.tags.join(', ')}</Card.Text>
      </Card.Body>
    </Card>
  );
}
