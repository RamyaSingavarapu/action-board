import { useEffect, useState } from 'react'
import { useNavigate } from "react-router";

export default function Boards() {
  const [message, setMessage] = useState('');
  let navigate = useNavigate();

  useEffect(() => {
    const tryBoard = async () => {
      try {
        const response = await fetch('/api/board/hello', {
          method: 'GET',
          credentials: "include",
        })

        if (response.status == 401) {
          navigate('/login');
        }

        const data = await response.text()
        setMessage(data)
      } catch (error) {
        console.error('Error logging in:', error)
        setMessage('Login failed')
      }
    };

    tryBoard();
  }, []);

  return (
    <div className="boards-container">
      {message && <p>{message}</p>}
    </div>
  )
}
