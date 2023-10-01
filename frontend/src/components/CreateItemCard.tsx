import {Button, Card, Form} from 'react-bootstrap';
import {useState} from 'react';
import {Item} from '../types/Item';
import itemsService from '../ItemsService';

type CreateItemProps = {
  onCreate: () => void;
};

export function CreateItemCard({onCreate}: CreateItemProps) {
  const [item, setItem] = useState<Item>({name: '', tags: []});

  const onSubmit = () => {
    itemsService.create(item).then(() => onCreate());
  };

  return (
    <Card data-bs-theme="dark">
      <Form>
        <Card.Header as="h5">Create New Item</Card.Header>
        <Card.Img variant="top" src="https://placehold.co/180x100"/>
        <Card.Body className="rounded mx-2">
          <Card.Title>
            <Form.Group controlId="validationCustom01">
              <Form.Label>Item Name</Form.Label>
              <Form.Control
                required
                type="text"
                name="name"
                onChange={(event) => {
                  console.log(event.currentTarget.value);
                  setItem({...item, name: event.currentTarget.value});
                }}
                placeholder="Item Name"
              />
              <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
            </Form.Group>
          </Card.Title>
          <Card.Text>
            <Form.Group controlId="validationCustom01">
              <Form.Label>Tags</Form.Label>
              {item.tags.length > 0 ? <p>{item.tags.join(', ')}</p> : <p>No tags</p>}
              <Form.Control
                type="text"
                name="tags"
                placeholder="Item Tags"
                onKeyUp={(event) => {
                  if (event.key === 'Enter') {
                    const tag = event.currentTarget.value;
                    item.tags.push(tag);
                    setItem({...item});
                    event.currentTarget.value = '';
                  }
                }}
              />
              <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
            </Form.Group>
          </Card.Text>
          <Card.Footer>
            <Button className="btn-primary float-end" onClick={onSubmit}>
              Submit
            </Button>
          </Card.Footer>
        </Card.Body>
      </Form>
    </Card>
  );
}
